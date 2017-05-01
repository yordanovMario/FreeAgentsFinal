document.addEventListener("DOMContentLoaded", function(){
    showNotifications();
});

var showNotifications = function() {
	var box = document.querySelector('.notification-box');
	
	if (box) {
		box.addEventListener('click', function() {
			document.querySelector('.notif-menu').classList.toggle('toggle-menu');
			if(document.querySelector('.notif-menu').className.indexOf('toggle-menu') > -1) {
				document.querySelector('.notif-menu').classList.remove('view-menu-not');
				document.querySelector('.notif-menu').classList.add('view-menu');
			} else {
				document.querySelector('.notif-menu').classList.remove('view-menu');
				document.querySelector('.notif-menu').classList.add('view-menu-not');
			}
		});
	} else {
		return;
	}
}