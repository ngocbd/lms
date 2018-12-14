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

// information course
const docCourseRef = db.collection("courses");
const courseId = document.querySelector("#course_code");
const authorId = document.querySelector("#author");
const description = document.querySelector("#description");
const lecturerId = document.querySelector("#lecturer");
const name = document.querySelector("#name");
const price = document.querySelector("#price");
const time = document.querySelector("#time");
 
// information part course
const docPartRef = db.collection("contents");
const contentId = document.querySelector("#part_code");
const name_part = document.querySelector("#name_part");
const des_part = document.querySelector("#des_part");

// information lesson
const docLessonRef = db.collection("lessons");
const lessonId = document.querySelector("#lesson_code");
const name_lesson = document.querySelector("#name_lesson");
const des_lesson = document.querySelector("#des_lesson");

// valid
var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

 
const saveBtn = document.querySelector("#saveButton");

saveBtn.addEventListener("click", function(){
	// course
	const _courseId = courseId.value;
	const author = authorId.value;
	const des = description.value;
	var radioValue = $("input[name='hot']:checked").val();
	console.log(radioValue);
	var hotCourseStatus = radioValue;
	console.log(hotCourseStatus);
	if(hotCourseStatus != null){
		hotCourseStatus = 1;
		console.log(hotCourseStatus);
	}else{
		hotCourseStatus = 0;
		console.log(hotCourseStatus);
	}
	const _lecturerId = lecturerId.value;
	const _name = name.value;
	const _price = price.value;
	const _time = time.value;
	
	// valid
	if (_lecturerId.length < 6) {
		/* alert('Please enter an email address.'); */
		Swal({
			title : 'Error!',
			text : 'Please enter an email lecture!',
			type : 'error',
			confirmButtonText : 'OK'
		});
		return;
	}
	if (reg.test(_lecturerId) == false) {
		/* alert('Invalid lecturer'); */
		Swal({
			title : 'Error!',
			text : 'Invalid Email Lecturer!',
			type : 'error',
			confirmButtonText : 'OK'
		});
		return;
	}
	
	// content
	const _contentId = contentId.value;
	const _name_part = name_part.value;
	const _des_part = des_part.value;
	
	// lesson
	const _lessonId = lessonId.value;
	const _name_lesson = name_lesson.value;
	const _des_lesson = des_lesson.value;
	
	docCourseRef.doc(_courseId).set({
    	author: author,
    	description: des,
    	hotCourse: hotCourseStatus,
    	lecturerId: _lecturerId,
    	name: _name,
    	price: _price,
    	time: _time
    }).then(function(docCourseRef){
    	Swal({
			title : 'Success',
			text : 'Create success!',
			type : 'success',
			confirmButtonText : 'OK'
		});
    }).catch(function(error){
    	Swal({
			title : 'Error!',
			text : 'Got an error!',
			type : 'error',
			confirmButtonText : 'OK'
		});
    	return;
    });
    

	
	// test
	var contentId = document.querySelector(".part_code");
	
	for (var i = 0, length = contentId.length; i < length; i++)
	{
	 if (contentId[i].value)
	 {
	  
	  break;
	 }
	}
	// end test
	
	// contents
	docPartRef.doc(_contentId).set({
		courseId: _courseId,
    	name: _name_part,
    	description: _des_part
    }).then(function(docPartRef){
    	console.log("create part success");
    }).catch(function(error){
    	console.log("error part save");
    	return;
    });
	
	// lessons
	docLessonRef.doc(_lessonId).set({
		contentId: _contentId,
    	name: _name_lesson,
    	description: _des_lesson
    }).then(function(docLessonRef){
    	console.log("create lesson success");
    }).catch(function(error){
    	console.log("error lesson save");
    	return;
    });
	
	
})
