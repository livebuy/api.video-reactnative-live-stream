package video.api.reactnative.livestream

import android.util.Log
import android.view.View
import video.api.reactnative.livestream.utils.getCameraFacing
import video.api.reactnative.livestream.utils.getResolution
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.common.MapBuilder
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import video.api.livestream.ApiVideoLiveStream
import video.api.livestream.interfaces.IConnectionChecker
import video.api.livestream.models.AudioConfig
import video.api.livestream.models.VideoConfig
import video.api.livestream.enums.CameraFacingDirection

class ReactNativeLivestreamViewManager : SimpleViewManager<View>(), IConnectionChecker {
  override fun getName() = "ReactNativeLivestreamView"

  private var liveStreamKey = ""
  private var rtmpServerUrl: String? = null

  private lateinit var context: ThemedReactContext
  private lateinit var view: ReactNativeLivestreamView
  private lateinit var apiVideoLivestreamManager: ApiVideoLivestreamManager
  private val apiVideoLiveStream: ApiVideoLiveStream?
    get() = apiVideoLivestreamManager.apiVideoLiveStream

  override fun createViewInstance(reactContext: ThemedReactContext): View {
    context = reactContext
    view = ReactNativeLivestreamView(reactContext)
    apiVideoLivestreamManager =
      ApiVideoLivestreamManager(context, this, view.findViewById(R.id.apivideo_view))

    return view
  }

  override fun receiveCommand(root: View, commandId: Int, args: ReadableArray?) {
    super.receiveCommand(root, commandId, args)

    when (commandId) {
      ViewProps.COMMAND_START_LIVE -> startStreaming()
      ViewProps.COMMAND_STOP_LIVE -> stopStreaming()
      ViewProps.ENABLE_AUDIO -> enableAudio()
      ViewProps.DISABLE_AUDIO -> disableAudio()
      ViewProps.SWITCH_CAMERA -> switchCamera()
      else -> {
        throw IllegalArgumentException("Unsupported command %d received by %s. $commandId")
      }
    }
  }

  override fun getCommandsMap(): MutableMap<String, Int> {
    return MapBuilder.of(
      ViewProps.START_STREAMING_FROM_MANAGER, ViewProps.COMMAND_START_LIVE,
      ViewProps.STOP_STREAMING_FROM_MANAGER, ViewProps.COMMAND_STOP_LIVE,
      ViewProps.ENABLE_AUDIO_FROM_MANAGER, ViewProps.ENABLE_AUDIO,
      ViewProps.DISABLE_AUDIO_FROM_MANAGER, ViewProps.DISABLE_AUDIO,
      ViewProps.SWITCH_CAMERA_FROM_MANAGER, ViewProps.SWITCH_CAMERA
    )
  }

  @ReactProp(name = ViewProps.LIVESTREAM_KEY)
  fun setLiveStreamKey(view: View, newLiveStreamKey: String) {
    if (newLiveStreamKey == liveStreamKey) return
    liveStreamKey = newLiveStreamKey
  }

  @ReactProp(name = ViewProps.RTMP_URL)
  fun setRtmpServerUrl(view: View, newRtmpServerUrl: String) {
    if (newRtmpServerUrl == rtmpServerUrl) return
    rtmpServerUrl = newRtmpServerUrl
  }

  @ReactProp(name = ViewProps.VIDEO_CONFIG)
  fun setVideoConfig(view: View, videoMap: ReadableMap) {
    apiVideoLivestreamManager.videoConfig = VideoConfig(
      bitrate = videoMap.getInt(ViewProps.BITRATE),
      resolution = videoMap.getString(ViewProps.RESOLUTION)?.getResolution()!!,
      fps = videoMap.getInt(ViewProps.FPS)
    )
  }

  @ReactProp(name = ViewProps.CAMERA)
  fun setCamera(view: View, newVideoCameraString: String) {
    val newVideoCamera = newVideoCameraString.getCameraFacing()
    /*
     * Video and audio config have not been passed to the Livestream manager.
     * apiVideoLiveStream might not be created here.
     */
    apiVideoLiveStream?.let {
      if (newVideoCamera == it.camera) return
      it.camera = newVideoCamera
    } ?: run {
      apiVideoLivestreamManager.initialCamera = newVideoCamera
    }
  }

  @ReactProp(name = ViewProps.AUDIO_CONFIG)
  fun setAudioConfig(view: View, audioMap: ReadableMap) {
    apiVideoLivestreamManager.audioConfig = AudioConfig(
      bitrate = audioMap.getInt(ViewProps.BITRATE),
      sampleRate = audioMap.getInt(ViewProps.SAMPLE_RATE),
      stereo = audioMap.getBoolean(ViewProps.IS_STEREO),
      echoCanceler = true,
      noiseSuppressor = true
    )
  }

  private fun startStreaming() {
    apiVideoLiveStream?.startStreaming(liveStreamKey, rtmpServerUrl)
  }

  private fun stopStreaming() {
    apiVideoLiveStream?.stopStreaming()
  }

  private fun disableAudio() {
    apiVideoLiveStream?.isMuted = true
  }

  private fun enableAudio() {
    apiVideoLiveStream?.isMuted = false
  }

  private fun switchCamera() {
    if (apiVideoLiveStream?.camera == CameraFacingDirection.BACK) {
      apiVideoLiveStream?.camera = CameraFacingDirection.FRONT
    } else {
      apiVideoLiveStream?.camera = CameraFacingDirection.BACK
    }
  }

  override fun onConnectionSuccess() {
    Log.i(this::class.simpleName, "Connection success")
  }

  override fun onConnectionFailed(reason: String) {
    Log.e(this::class.simpleName, "Connection failed due to $reason")
  }

  override fun onConnectionStarted(url: String) {
    Log.w(this::class.simpleName, "Connection started")
  }

  override fun onDisconnect() {
    Log.w(this::class.simpleName, "Disconnected")
  }

  override fun onAuthError() {
    Log.e(this::class.simpleName, "Authentication failed")
  }

  override fun onAuthSuccess() {
    Log.i(this::class.simpleName, "Authentication is successful")
  }
}
