<template>
	<form action="javascript:void(0)">
		<div class="mb-4">
			<h3 class="text-center">Register</h3>
		</div>
		<div class="input-group">
			<span class="input-group-text" id="register-username-addon"><img src="/face_black_24dp.svg"></span>
			<input type="text" class="form-control" id="registration-username-input" placeholder="username" v-model="username" aria-label="Username" aria-describedby="registration-username-help">
		</div>
		<div id="registration-username-help" class="form-text mb-3">Username must be 8-25 characters and may only contain letters and numbers.</div>
		<div class="input-group mb-4">
			<span class="input-group-text" id="register-email-addon"><img src="/email_black_24dp.svg"></span>
			<input type="email" class="form-control" id="registration-email-input" placeholder="email" v-model="email" aria-label="Email">
		</div>
		<div class="input-group mb-1">
			<span class="input-group-text" id="register-password-addon"><img src="/lock_black_24dp.svg"></span>
			<input type="password" class="form-control" id="registration-password-input" placeholder="password" v-model="password" aria-label="Password">
		</div>
		<div class="input-group mb-4">
			<span class="input-group-text" id="register-password-confirm-addon"><img src="/done_black_24dp.svg"></span>
			<input type="password" class="form-control" id="registrationPasswordConfirm" placeholder="confirm password"  v-model="passwordConfirm" aria-label="Confirm Password">
		</div>
		<p class="form-text text-danger" v-if="error">{{error}}</p>
		<div class="text-center">
			<button @click="submit();" class="btn btn-primary ps-5 pe-5">Register</button>
		</div>
	</form>
</template>

<script>
import $ from 'jquery';
import Cookies from 'js-cookie'

$(function () {
  // eslint-disable-next-line no-unused-vars
  $(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader("X-XSRF-TOKEN", Cookies.get('XSRF-TOKEN'));
  });
});

export default {
	name: 'RegistrationForm',
	data() {
		return {
			error: null,
			username: '',
			email: '',
			password: '',
			passwordConfirm: ''
		}
	},
	methods: {
		validateInput() {
			// Validate username length
			if(this.username.length < 8 || this.username.length > 25) {
				this.error = 'Username must be 8 to 25 characters long.'
				return false;
			}

			// Validate username characters
			var usernameRegex = /^[A-Za-z0-9]*$/;
			if(!usernameRegex.test(this.username)) {
				this.error = 'Username contains an invalid character. Only letters and numbers may be used.';
				return false;
			}

			// Validate email
			var emailRegex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			if(!emailRegex.test(this.email)) {
				this.error = 'Invalid email.';
				return false;
			}

			// Validate password
			if(this.password.length < 10 || this.password.length > 75) {
				this.error = 'Password must be 10 to 75 characters in length.';
				return false;
			}

			if(this.password !== this.passwordConfirm) {
				this.error = 'Passwords do not match.';
				return false;
			}

			// Everything is good to go
			this.error = null
			return true;
		},

		submit() {
			if(this.validateInput()) {
				$.post(
					"/register",
					{
						username: this.username,
						email: this.email,
						password: this.password,
						passwordConfirm: this.passwordConfirm
					},
					(data) => {
						if(data.isError) {
							this.error = data.message;
						}
						else {
							alert(data.message);
						}
					}
				);
			}
		}
	}
}
</script>

<style scoped>

</style>