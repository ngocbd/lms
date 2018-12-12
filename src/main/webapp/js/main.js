var config = {
    apiKey: "AIzaSyC5AKG70BWISQlGvzsWtJsp_GkEXwawNFQ",
    authDomain: "freecodeschoollms.firebaseapp.com",
    databaseURL: "https://freecodeschoollms.firebaseio.com",
    projectId: "freecodeschoollms",
    storageBucket: "freecodeschoollms.appspot.com",
    messagingSenderId: "574690181313"
  };


firebase.initializeApp(config);
var db = firebase.firestore();

//Disable deprecated features
db.settings({
	timestampsInSnapshots: true
});

firebase.auth().setPersistence(firebase.auth.Auth.Persistence.LOCAL);
  
  function showLoginUI()
  {
  	document.getElementById('loader').style.display = 'block';
var ui = new firebaseui.auth.AuthUI(firebase.auth());	
var uiConfig = {
		  callbacks: {
		    signInSuccessWithAuthResult: function(authResult, redirectUrl) {
		      // User successfully signed in.
		      // Return type determines whether we continue the redirect automatically
		      // or whether we leave that to developer to handle.
		      console.log(authResult.additionalUserInfo);
		      return false;
		    },
		    uiShown: function() {
		      // The widget is rendered.
		      // Hide the loader.
		      document.getElementById('loader').style.display = 'none';
		    }
		  },
		  // Will use popup for IDP Providers sign-in flow instead of the default, redirect.
		  signInFlow: 'popup',
		  signInSuccessUrl :'/dashboard',
		  signInOptions: [
		    // Leave the lines as is for the providers you want to offer your users.
		    firebase.auth.GoogleAuthProvider.PROVIDER_ID
		    
		  ],
		  // Terms of service url.
		  tosUrl: '/tos',
		  // Privacy policy url.
		  privacyPolicyUrl: '/privacy'
		};
ui.start('#firebaseui-auth-container',uiConfig);
  	return false;
  }
  function signout()
  {
  	firebase.auth().signOut().then(function() {
  		  document.getElementById('sign-in').innerHTML = '<a href="/login" onclick="return showLoginUI()">Login</a>';
	}).catch(function(error) {
	  // An error happened.
  		});
  	return false;
  }
  
  initApp = function() {
      firebase.auth().onAuthStateChanged(function(user) {
        if (user) {
          // User is signed in.
      var displayName = user.displayName;
      var email = user.email;
      var emailVerified = user.emailVerified;
      var photoURL = user.photoURL;
      var uid = user.uid;
      var phoneNumber = user.phoneNumber;
      var providerData = user.providerData;
      user.getIdToken().then(function(accessToken) {
        //document.getElementById('sign-in-status').textContent = 'Signed in';
        document.getElementById('sign-in').innerHTML = '<a href="/signout"  onclick="return signout()" class="button-register" data-toggle="modal" data-target="#k-popup-account-register" data-ajax="" data-push-state="false">Đăng xuất</a>';
        document.getElementById('account-details').textContent = displayName;
        /* document.getElementById('account-details').textContent = JSON.stringify({
          displayName: displayName,
          email: email,
          emailVerified: emailVerified,
          phoneNumber: phoneNumber,
          photoURL: photoURL,
          uid: uid,
          accessToken: accessToken,
          providerData: providerData
        }, null, '  '); */
      });
    } else {
      // User is signed out.
      //document.getElementById('sign-in-status').textContent = 'Signed out';
      document.getElementById('sign-in').innerHTML = '<a href="/login" onclick="return showLoginUI()" class="button-login" data-toggle="modal" data-target="#k-popup-account-login" data-ajax="" data-push-state="false">Đăng nhập</a>';
      document.getElementById('account-details').textContent = '';
    }
  }, function(error) {
    console.log(error);
  });
};

window.addEventListener('load', function() {
  initApp()
});