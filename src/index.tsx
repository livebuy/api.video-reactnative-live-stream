import React, { forwardRef, useImperativeHandle, useRef } from 'react';
import {
  requireNativeComponent,
  ViewStyle,
  UIManager,
  findNodeHandle,
} from 'react-native';

type ReactNativeLivestreamProps = {
  style: ViewStyle;
  liveStreamKey: string;
  rtmpServerUrl?: string;
  video: {
    bitrate: number;
    fps: number;
    resolution: '240p' | '360p' | '480p' | '720p' | '1080p' | '2160p';
  };
  audio: {
    bitrate: number;
    samplerate: 8000 | 16000 | 32000 | 44100 | 48000;
    isStereo: boolean;
  };
  camera?: 'front' | 'back';
};

export type ReactNativeLivestreamMethods = {
  startStreaming: () => void;
  stopStreaming: () => void;
  enableAudio: () => void;
  disableAudio: () => void;
  switchCamera: () => void;
};

export const ReactNativeLivestreamViewNative = requireNativeComponent<ReactNativeLivestreamProps>(
  'ReactNativeLivestreamView'
);

ReactNativeLivestreamViewNative.displayName = 'ReactNativeLivestreamViewNative';

const LivestreamView = forwardRef<
  ReactNativeLivestreamMethods,
  ReactNativeLivestreamProps
>(({ style, rtmpServerUrl, liveStreamKey, video, audio, camera }, forwardedRef) => {
  const nativeRef = useRef<typeof ReactNativeLivestreamViewNative | null>(null);

  useImperativeHandle(forwardedRef, () => ({
    startStreaming: () => {
      UIManager.dispatchViewManagerCommand(
        findNodeHandle(nativeRef.current),
        UIManager.getViewManagerConfig('ReactNativeLivestreamView').Commands
          .startStreamingFromManager,
        []
      );
    },
    stopStreaming: () => {
      UIManager.dispatchViewManagerCommand(
        findNodeHandle(nativeRef.current),
        UIManager.getViewManagerConfig('ReactNativeLivestreamView').Commands
          .stopStreamingFromManager,
        []
      );
    },
    enableAudio: () => {
      UIManager.dispatchViewManagerCommand(
        findNodeHandle(nativeRef.current),
        UIManager.getViewManagerConfig('ReactNativeLivestreamView').Commands
          .enableAudioFromManager,
        []
      );
    },
    disableAudio: () => {
      UIManager.dispatchViewManagerCommand(
        findNodeHandle(nativeRef.current),
        UIManager.getViewManagerConfig('ReactNativeLivestreamView').Commands
          .disableAudioFromManager,
        []
      );
    },
    switchCamera: () => {
      UIManager.dispatchViewManagerCommand(
        findNodeHandle(nativeRef.current),
        UIManager.getViewManagerConfig('ReactNativeLivestreamView').Commands
          .switchCameraFromManager,
        []
      );
    },
  }));

  return (
    <ReactNativeLivestreamViewNative
      style={style}
      video={video}
      audio={audio}
      camera={camera}
      liveStreamKey={liveStreamKey}
      rtmpServerUrl={rtmpServerUrl}
      ref={nativeRef as any}
    />
  );
});

export { LivestreamView };
