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
		},
		'admin': {
			entry: 'src/pages/admin/main.js',
			template: 'public/admin.html',
			filename: 'admin.html',
			title: 'Admin',
			chunks: ['chunk-vendors', 'chunk-common', 'admin']
		}
	},
	devServer: {
		port: 3000,
		proxy: "http://localhost:8080"
	},
	configureWebpack: {
		devtool: 'source-map'
	}
}