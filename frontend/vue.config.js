module.exports = {
	pages: {
		'index': {
			entry: 'src/pages/index/main.js',
			template: 'public/main.html',
			filename: 'index.html',
			title: 'Index',
			chunks: ['chunk-vendors', 'chunk-common', 'index']
		},
		'login': {
			entry: 'src/pages/login/main.js',
			template: 'public/main.html',
			filename: 'login.html',
			title: 'Login',
			chunks: ['chunk-vendors', 'chunk-common', 'login']
		},
		'register': {
			entry: 'src/pages/register/main.js',
			template: 'public/main.html',
			filename: 'register.html',
			title: 'Register',
			chunks: ['chunk-vendors', 'chunk-common', 'register']
		}
	},
	devServer: {
		port: 3000
	}
}