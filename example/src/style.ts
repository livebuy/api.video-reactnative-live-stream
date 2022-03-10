import { StyleSheet } from 'react-native'

export default (
	streaming: boolean,
	android: boolean
) => StyleSheet.create({
	container: {
		flex: 1,
		alignItems: 'center',
		justifyContent: 'center',
	},
	livestreamView: {
		flex: 1,
		backgroundColor: 'black',
		alignSelf: 'stretch'
	},
	streamButton: {
		display: 'flex',
		alignItems: 'center',
		justifyContent: 'center',
		borderRadius: 60,
		width: streaming ? 50 : undefined,
		height: streaming ? 50 : undefined,
		backgroundColor: streaming ? undefined : '#8137FF',
		paddingVertical: streaming ? undefined : 15,
		paddingHorizontal: streaming ? undefined : 25
	},
	streamText: {
		color: '#FFFFFF',
		fontSize: 16,
		fontWeight: '700'
	},
	resolutionButton: {
		display: 'flex',
		alignItems: 'center',
		justifyContent: 'center',
		borderRadius: 50,
		backgroundColor: 'yellow',
		width: 50,
		height: 50,
	},
	audioButton: {
		display: 'flex',
		alignItems: 'center',
		justifyContent: 'center',
		width: 50,
		height: 50,
	},
	cameraButton: {
		display: 'flex',
		alignItems: 'center',
		justifyContent: 'center',
		width: 50,
		height: 50,
	},
	settingsButton: {
		position: 'absolute',
		display: 'flex',
		flexDirection: 'row',
		alignItems: 'center',
		top: android ? 20 : 60,
		right: 30,
		minHeight: 50
	},
	warningContainer: {
		paddingVertical: 10,
		paddingHorizontal: 15,
		backgroundColor: '#FF0001',
		borderRadius: 60,
		borderColor: '#FFFFFF',
		borderWidth: 1,
		marginRight: 20
	},
	warning: {
		color: '#FFFFFF',
		fontSize: 10,
		fontWeight: '700'
	}
})

interface IButtonParams {
	top?: number,
	bottom?: number,
	left?: number,
	right?: number
}

export const button = (position: IButtonParams) => StyleSheet.create({
	container: {
		position: 'absolute',
		top: position.top,
		bottom: position.bottom,
		left: position.left,
		right: position.right
	}
})
