<template>
	<form action="javascript:void(0)">
		<div class="mb-4">
			<h3 class="text-center">Login</h3>
		</div>
		<div class="input-group mb-1">
			<span class="input-group-text" id="login-username-addon"><img src="/face_black_24dp.svg"></span>
			<input type="text" class="form-control" id="login-username-input" placeholder="username" aria-label="Username" aria-describedby="login-username-addon" v-model="username">
		</div>
		<div class="input-group mb-4">
			<span class="input-group-text" id="login-password-addon"><img src="/lock_black_24dp.svg"></span>
			<input type="password" class="form-control" id="login-password-input" placeholder="password" aria-label="Password" aria-describedby="login-password-addon" v-model="password">
		</div>
		<p class="form-text text-danger" v-if="error">{{error}}</p>
		<div class="text-center">
			<button @click="submit();" class="btn btn-primary ps-5 pe-5">Login</button>
		</div>
	</form>
</template>

<script>
import $ from 'jquery';

export default {
	name: 'LoginForm',
	data() {
		return {
			error: null,
			username: '',
			password: ''
		}
	},
	methods: {
		submit() {
			$.post(
				"/login",
				{
					username: this.username,
					password: this.password
				},
				(data, status, xhr) => {
					var hasError = xhr.getResponseHeader("login-error") === 'true';
					if(hasError) {
						this.error = "Invalid login credentials.";
					}
					else {
						var redirectLoc = xhr.getResponseHeader("login-redirect");
						window.location.href = redirectLoc;
					}
				}
			)
		}
	}
}
</script>

<style scoped>

</style>