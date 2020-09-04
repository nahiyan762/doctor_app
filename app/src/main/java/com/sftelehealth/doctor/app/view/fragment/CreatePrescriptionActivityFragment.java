package com.sftelehealth.doctor.app.view.fragment;


import androidx.lifecycle.Observer;
import android.content.DialogInterface;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.sftelehealth.doctor.R;
import com.sftelehealth.doctor.app.listener.PrescriptionEventListener;
import com.sftelehealth.doctor.app.utils.Constant;
import com.sftelehealth.doctor.app.view.activity.BaseAppCompatActivity;
import com.sftelehealth.doctor.app.view.activity.PrescriptionActivity;
import com.sftelehealth.doctor.app.view.adapter.MedicineTypesSpinnerAdapter;
import com.sftelehealth.doctor.app.view.helper.SnackbarHelper;
import com.sftelehealth.doctor.app.view.utils.ActivityUtils;
import com.sftelehealth.doctor.app.view.viewmodel.PrescriptionActivityFragmentViewModel;
import com.sftelehealth.doctor.databinding.FragmentCreatePrescriptionBinding;
import com.sftelehealth.doctor.databinding.MedicineItemLayoutBinding;
import com.sftelehealth.doctor.domain.model.Medicine;
import com.sftelehealth.doctor.domain.model.MedicineType;

import static com.sftelehealth.doctor.app.utils.Constant.CONSULT_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePrescriptionActivityFragment extends Fragment implements PrescriptionEventListener{


    private PrescriptionActivityFragmentViewModel viewModel;
    private FragmentCreatePrescriptionBinding binding;

    public CreatePrescriptionActivityFragment() {}

    public static CreatePrescriptionActivityFragment newInstance() {
        return new CreatePrescriptionActivityFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_prescription, container, false);

        viewModel = ((PrescriptionActivity)getActivity()).obtainViewModel(getActivity());

        if(getActivity().getIntent().getIntExtra(CONSULT_ID, 0) != 0)
            viewModel.prescription.get().setConsultId(getActivity().getIntent().getIntExtra(CONSULT_ID, 0));

        if(!TextUtils.isEmpty(viewModel.prescription.get().getHistory()))
            ActivityUtils.setUpToolbarForBackPress((BaseAppCompatActivity) getActivity(), "Edit Medical Advice");
        else
            ActivityUtils.setUpToolbarForBackPress((BaseAppCompatActivity) getActivity(), "New Medical Advice");

        setUpObservers();
        setUpData();

        return binding.getRoot();
    }

    public void setUpData() {
        binding.setPrescription(viewModel.prescription.get());
        binding.medicineFormLayout.setMedicine(viewModel.medicine.get());
        binding.setListener(this);
        viewModel.getGetMedicineTypes();
        viewModel.getDoctor(getActivity().getIntent().getIntExtra(Constant.DOCTOR_CATEGORY_ID, 0));
        prepareAddedMedicinesLayout();
    }

    public void setUpObservers() {

        viewModel.medicineTypesFetched.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                //ArrayAdapter<String> medicineTypesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, viewModel.medicineTypes.get());

                /*ArrayList<String> medicineTypes = new ArrayList<>();
                for (MedicineType medicineType :
                        viewModel.medicineTypes) {
                    medicineTypes.add(medicineType.getType());
                }*/
                MedicineTypesSpinnerAdapter medicineTypesAdapter = new MedicineTypesSpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, viewModel.medicineTypes);
                medicineTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.medicineFormLayout.medicineCategory.setAdapter(medicineTypesAdapter);
            }
        });

        viewModel.doctorDetailsFetched.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean doctorDetailsFetched) {
                if(viewModel.isDietitianCategory) {
                    // hide the layout for medicines
                    binding.medicineFormLayout.desktopAppText.setVisibility(View.VISIBLE);
                    binding.medicineFormLayout.medicineFormContainer.setVisibility(View.GONE);

                    // This is fetched when the view is created, so if history is present then prescription is being edited
                    if(!TextUtils.isEmpty(viewModel.prescription.get().getHistory())) {
                        if (viewModel.prescription.get().getDietChart().size() > 0) {
                            binding.medicineFormLayout.desktopAppText.setText(R.string.diet_chart_provided_by_desktop_app);
                        } else {
                            binding.medicineFormLayout.desktopAppText.setText(R.string.use_desktop_app_to_edit);
                        }
                    } else
                        binding.medicineFormLayout.desktopAppText.setText(R.string.use_desktop_app_to_create_diet_chart_in_proper_format);
                } else {
                    // display the layout for medicines
                    binding.medicineFormLayout.desktopAppText.setVisibility(View.GONE);
                    binding.medicineFormLayout.medicineFormContainer.setVisibility(View.VISIBLE);
                }
            }
        });

        viewModel.notifyPrescriptionInvalidMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // show error stating that validation error message
                SnackbarHelper.getCustomSnackBar(binding.getRoot(), s, null, SnackbarHelper.SnackbarTypes.ERROR).show();
            }
        });
    }

    public void addMedicine() {

        viewModel.medicine.get().setType(((MedicineType)binding.medicineFormLayout.medicineCategory.getSelectedItem()).getType());
        addMedicineToViewModel();
        // get the filled in details and validate that
        //viewModel.prescription.get().getMedicine().add(viewModel.medicine.get());

        // if successfully validated then add the medicine to the prescription object
        // add the medicine object by generating a new UI for it and clear the UI for adding more medicines
    }

    public void previewPrescription() {
        // validate the model to check whether History and Advice are written
        if(viewModel.validatePrescription()) {

            // confirm whether doctor wants to write a prescription without adding medicines.
            if(!viewModel.isDietitianCategory && viewModel.prescription.get().getMedicine().size() == 0) {

                new androidx.appcompat.app.AlertDialog.Builder(getContext())
                        .setMessage("Do you want to add medical advice without medicine?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ((PrescriptionActivity) getActivity()).showPreview(false);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            } else {
                // show preview fragment with the prepared prescription
                ((PrescriptionActivity)getActivity()).showPreview(false);
            }
        }
    }

    private void addMedicineToViewModel() {

        // Check whether the duration is filled or not if medicines are filled and show appropriate message
        if((!TextUtils.isEmpty(viewModel.medicine.get().getDosageMorning()) || !TextUtils.isEmpty(viewModel.medicine.get().getDosageAfternoon()) || !TextUtils.isEmpty(viewModel.medicine.get().getDosageEvening())) && (TextUtils.isEmpty(viewModel.medicine.get().getDuration()) || viewModel.medicine.get().getDuration().equals("0"))) {
            // show error stating that duration must be filled if dosage has been filled
            SnackbarHelper.getCustomSnackBar(binding.getRoot(), "Duration for medicine must be filled", null, SnackbarHelper.SnackbarTypes.ERROR).show();
        } else if(TextUtils.isEmpty(viewModel.medicine.get().getName())) {
            // show error stating that medicine name must be filled
            SnackbarHelper.getCustomSnackBar(binding.getRoot(), "Medicine name must be filled", null, SnackbarHelper.SnackbarTypes.ERROR).show();
        } else if(TextUtils.isEmpty(viewModel.medicine.get().getInstruction())) {
            // show error stating that instructions name must be filled
            SnackbarHelper.getCustomSnackBar(binding.getRoot(), "Instructions must be filled", null, SnackbarHelper.SnackbarTypes.ERROR).show();
        } else {

            // if one of them is not empty and then all other dosage must be set to 0
            if (!TextUtils.isEmpty(viewModel.medicine.get().getDosageMorning()) || !TextUtils.isEmpty(viewModel.medicine.get().getDosageAfternoon()) || !TextUtils.isEmpty(viewModel.medicine.get().getDosageEvening())) {
                if(TextUtils.isEmpty(viewModel.medicine.get().getDosageMorning()))
                    viewModel.medicine.get().setDosageMorning("0");

                if(TextUtils.isEmpty(viewModel.medicine.get().getDosageAfternoon()))
                    viewModel.medicine.get().setDosageAfternoon("0");

                if(TextUtils.isEmpty(viewModel.medicine.get().getDosageEvening()))
                    viewModel.medicine.get().setDosageEvening("0");
            } else {
                viewModel.medicine.get().setDosageMorning("0");
                viewModel.medicine.get().setDosageAfternoon("0");
                viewModel.medicine.get().setDosageEvening("0");
            }

            // generate formatted dosage
            viewModel.medicine.get().getDosage();
            // viewModel.medicine.get().setDosageAfternoon("0");

            // add medicine to prescription
            viewModel.prescription.get().getMedicine().add(0, viewModel.medicine.get());
            viewModel.medicine.set(new Medicine());

            prepareAddedMedicinesLayout();
        }
    }

    public void removeMedicine(String medicineName) {
        // remove medicine from prescription
        for (int i=0; i < viewModel.prescription.get().getMedicine().size(); i++) {

            if(medicineName.equalsIgnoreCase(viewModel.prescription.get().getMedicine().get(i).getName()))
                viewModel.prescription.get().getMedicine().remove(i);
        }

        prepareAddedMedicinesLayout();
    }

    private void prepareAddedMedicinesLayout() {
        binding.addedMedicinesContainer.removeAllViews();

        // If its a dietician category then medicines must not be visible.
        if(!viewModel.prescription.get().isDietChart()) {
            for (Medicine tempMedicine : viewModel.prescription.get().getMedicine()) {

                MedicineItemLayoutBinding medicineItemBinding = DataBindingUtil.inflate(LayoutInflater.from(binding.getRoot().getContext()), R.layout.medicine_item_layout, binding.addedMedicinesContainer, false);
                medicineItemBinding.setMedicine(tempMedicine);

                if (TextUtils.isEmpty(tempMedicine.getDosageMorning()) && TextUtils.isEmpty(tempMedicine.getDosageAfternoon()) && TextUtils.isEmpty(tempMedicine.getDosageEvening()) || tempMedicine.getDosage().equals("0-0-0")) {
                    // if all dosages are empty and duration is not then the graphic should not be visible
                    medicineItemBinding.dosageGraphic.setVisibility(View.GONE);
                /*if(TextUtils.isEmpty(viewModel.medicine.get().getDuration())) {
                    // if duration is empty then do not show the dosage/duration
                    medicineItemBinding.dosage.setVisibility(View.INVISIBLE);
                } else {
                    // if duration is set then show the dosage/duration
                    medicineItemBinding.dosage.setText(viewModel.medicine.get().getDuration() + " days");
                }*/
                } else
                    medicineItemBinding.dosageGraphic.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(viewModel.medicine.get().getDuration())) // if duration is empty then do not show the dosage/duration
                    medicineItemBinding.dosage.setVisibility(View.GONE);
                else
                    medicineItemBinding.dosage.setVisibility(View.VISIBLE);

                medicineItemBinding.setListener(this);
                binding.addedMedicinesContainer.addView(medicineItemBinding.getRoot());
            }

            binding.medicineFormLayout.setMedicine(viewModel.medicine.get());
            //binding.medicineFormLayout.notifyPropertyChanged(BR.medicine);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        super.onPrepareOptionsMenu(menu);
    }
}