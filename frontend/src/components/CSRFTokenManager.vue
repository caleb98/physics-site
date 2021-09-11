<template>
	<div style="display: none;"></div>
</template>

<script>
import $ from 'jquery'
import Cookies from 'js-cookie'

$(function () {
  // eslint-disable-next-line no-unused-vars
  $(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader("X-XSRF-TOKEN", Cookies.get('XSRF-TOKEN'));
  });
});

export default {
	name: "CSRFTokenManager"
}
</script>

<style>

</style>