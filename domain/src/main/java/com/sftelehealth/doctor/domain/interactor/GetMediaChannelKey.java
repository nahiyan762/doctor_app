package com.sftelehealth.doctor.domain.interactor;

import com.sftelehealth.doctor.domain.executor.PostExecutionThread;
import com.sftelehealth.doctor.domain.executor.ThreadExecutor;
import com.sftelehealth.doctor.domain.model.response.MediaChannelResponse;
import com.sftelehealth.doctor.domain.repository.VideoRepository;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetMediaChannelKey extends UseCase<MediaChannelResponse, Map<String, String>> {

    private final VideoRepository videoRepository;

    @Inject
    GetMediaChannelKey(VideoRepository videoRepository, ThreadExecutor threadExecutor,
                       PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.videoRepository = videoRepository;
    }

    @Override
    Observable<MediaChannelResponse> buildUseCaseObservable(Map<String, String> stringMap) {
        return videoRepository.getMediaChannelKey(stringMap.get("channel_name"), stringMap.get("uid"), stringMap.get("patient_user_id"));
    }
}
