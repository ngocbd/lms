var config = {
	apiKey: "AIzaSyC5AKG70BWISQlGvzsWtJsp_GkEXwawNFQ",
    authDomain: "freecodeschoollms.firebaseapp.com",
    databaseURL: "https://freecodeschoollms.firebaseio.com",
    projectId: "freecodeschoollms",
    storageBucket: "freecodeschoollms.appspot.com",
    messagingSenderId: "574690181313"
};
firebase.initializeApp(config);
// Initialize Cloud Firestore through Firebase
var db = firebase.firestore();

// Disable deprecated features
db.settings({
  timestampsInSnapshots: true
});

var document = null;

const docRef = db.collection("courses");
const authorId = document.querySelector("#author");
const description = document.querySelector("#description");
const hotCourse = document.querySelector("#hotCourse");
const lecturerId = document.querySelector("#lecturer");
const name = document.querySelector("#name");
const price = document.querySelector("#price");
const time = document.querySelector("#time");
const user = document.querySelector("#user");

const saveBtn = document.querySelector("#saveButton");

saveBtn.addEventListener("click", function(){
	const author = authorId.value;
	const des = description.value;
	const hotCourseStatus = hotCourse.value;
	const _lecturerId = lecturerId.value;
	const _name = name.value;
	const _price = price.value;
	const _time = time.value;
	const _user = user.value;
    docRef.add({
    	author: author,
    	description: des,
    	hotCourse: hotCourseStatus,
    	lecturerId: _lecturerId,
    	name: _name,
    	price: _price,
    	time: _time,
    	userId: _user
    }).then(function(docRef){
    	console.log("status save!");
    }).catch(function(error){
    	console.log("Got an error", error);
    });
})
