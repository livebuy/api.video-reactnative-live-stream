package video.api.reactnative.livestream

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import video.api.livestream.ApiVideoLiveStream
import video.api.livestream.enums.CameraFacingDirection
import video.api.livestream.views.ApiVideoView
import video.api.livestream.interfaces.IConnectionChecker
import video.api.livestream.models.AudioConfig
import video.api.livestream.models.VideoConfig

class ApiVideoLivestreamManager(
  private val context: Context,
  private val connectionChecker: IConnectionChecker,
  private val view: ApiVideoView
) {
  var audioConfig: AudioConfig? = null
    set(value) {
      if (value != null) {
        field = value
        apiVideoLiveStream?.audioConfig = value
      }
    }

  var videoConfig: VideoConfig? = null
    set(value) {
      if (value != null) {
        field = value
        apiVideoLiveStream?.videoConfig = value
      }
    }

  var initialCamera = CameraFacingDirection.BACK

  private var _apiVideoLiveStream: ApiVideoLiveStream? = null

  val apiVideoLiveStream: ApiVideoLiveStream?
    get() {
      if (_apiVideoLiveStream == null) {
        _apiVideoLiveStream = buildOrNull()
      }
      return _apiVideoLiveStream
    }

  @SuppressLint("MissingPermission")
  private fun buildOrNull(): ApiVideoLiveStream? {
    return if ((audioConfig != null) && (videoConfig != null)) {
      ApiVideoLiveStream(
        context = context,
        connectionChecker = connectionChecker,
        initialAudioConfig = audioConfig!!,
        initialVideoConfig = videoConfig!!,
        initialCamera = initialCamera,
        apiVideoView = view
      )
    } else {
      null
    }
  }
}
