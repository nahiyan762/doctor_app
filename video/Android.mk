LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := video
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	/Users/rahul/Workspace/Android/doctor24x7-doctormvvm/video/src/main/jni/arm64-v8a/libagora-crypto.so \
	/Users/rahul/Workspace/Android/doctor24x7-doctormvvm/video/src/main/jni/arm64-v8a/libagora-rtc-sdk-jni.so \
	/Users/rahul/Workspace/Android/doctor24x7-doctormvvm/video/src/main/jni/arm64-v8a/libagora-sig-sdk-jni.so \
	/Users/rahul/Workspace/Android/doctor24x7-doctormvvm/video/src/main/jni/armeabi-v7a/libagora-crypto.so \
	/Users/rahul/Workspace/Android/doctor24x7-doctormvvm/video/src/main/jni/armeabi-v7a/libagora-rtc-sdk-jni.so \
	/Users/rahul/Workspace/Android/doctor24x7-doctormvvm/video/src/main/jni/armeabi-v7a/libagora-sig-sdk-jni.so \
	/Users/rahul/Workspace/Android/doctor24x7-doctormvvm/video/src/main/jni/x86/libagora-crypto.so \
	/Users/rahul/Workspace/Android/doctor24x7-doctormvvm/video/src/main/jni/x86/libagora-rtc-sdk-jni.so \
	/Users/rahul/Workspace/Android/doctor24x7-doctormvvm/video/src/main/jni/x86/libagora-sig-sdk-jni.so \

LOCAL_C_INCLUDES += /Users/rahul/Workspace/Android/doctor24x7-doctormvvm/video/src/fortisDev/jni
LOCAL_C_INCLUDES += /Users/rahul/Workspace/Android/doctor24x7-doctormvvm/video/src/fortisDevDebug/jni
LOCAL_C_INCLUDES += /Users/rahul/Workspace/Android/doctor24x7-doctormvvm/video/src/main/jni
LOCAL_C_INCLUDES += /Users/rahul/Workspace/Android/doctor24x7-doctormvvm/video/src/debug/jni

include $(BUILD_SHARED_LIBRARY)
