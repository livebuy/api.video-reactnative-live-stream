package video.api.reactnative.livestream

object ViewProps {
  const val LIVESTREAM_KEY = "liveStreamKey"
  const val RTMP_URL = "rtmpServerUrl"

  const val AUDIO_CONFIG = "audio"
  const val VIDEO_CONFIG = "video"

  const val CAMERA = "camera"

  const val BITRATE = "bitrate"
  const val RESOLUTION = "resolution"
  const val FPS = "fps"
  const val SAMPLE_RATE = "sampleRate"
  const val IS_STEREO = "isStereo"

  const val COMMAND_START_LIVE = 1
  const val COMMAND_STOP_LIVE = 2
  const val ENABLE_AUDIO = 3
  const val DISABLE_AUDIO = 4
  const val SWITCH_CAMERA = 5

  const val START_STREAMING_FROM_MANAGER = "startStreamingFromManager"
  const val STOP_STREAMING_FROM_MANAGER = "stopStreamingFromManager"
  const val ENABLE_AUDIO_FROM_MANAGER = "enableAudioFromManager"
  const val DISABLE_AUDIO_FROM_MANAGER = "disableAudioFromManager"
  const val SWITCH_CAMERA_FROM_MANAGER = "switchCameraFromManager"
}
