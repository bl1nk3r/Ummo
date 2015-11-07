Firebase = require("firebase");
function QEr(user,callback){
	var fireB = new Firebase('https://ummo.firebaseio.com/qEr/users/'+user.cellNum);

	fireB.set(user);
}
function clenQs(cell,callback){
	var fireBQs = new Firebase('https://ummo.firebaseio.com/qEr/users/'+cell+"/joinedQs");
	fireBQs.once("value",function (snap) {
		var arr = snap.val();
		callback(snap.val());
		for (var i = 0; i < arr.length; i++) {
			var fireB = new Firebase('https://ummo.firebaseio.com/qMaster/users/'+arr[i]);
			fireB.once("value",function(snapshot){
				if(snapshot.hasChild('/managedQ/qErs/'+cell)){
					console.log("Exixts");
				}

				else {
					console.log(snapshot.key()+"Has to be cleaned!");
					snap.forEach(function(childSnapshot) {
							var childData = childSnapshot.val();
							console.log(childData);
					});
				}
			})
		}
		console.log(snap.val());
	});
}

function joinQ(user,vq,callback) {

	var fireB = new Firebase('https://ummo.firebaseio.com/qMaster/users/'+vq+'/managedQ/qErs');
	fireB.once("value",function (snap) {
		if (snap.child(user).exists()) { //Checks if user hasnt joined q yet
			console.log("Already Registered");
		}
		else {
		fireB.child(user).set(snap.numChildren()); //User has not Joined
	}
	})

	var myFire = new Firebase('https://ummo.firebaseio.com/qEr/users/'+user+'/joinedQs')

	myFire.once("value",function (snap) {
			myFire.child(snap.numChildren()).set(vq);
	});
}

function update(user,callback) {
	var fireB = new Firebase('https://ummo.firebaseio.com/qMaster/users/'+qMaster.cellNum);
	fireB.on("value",function (snapshot) {callback(snapshot.val())},function (err) {callback(err)});
	}

function leaveQ(user,vq) {

	var fireB = new Firebase('https://ummo.firebaseio.com/qMaster/users/'+vq+"/managedQ/qErs");
	fireB.once("value",function (snapshot) {
	 var pos=snapshot.child(user).val();

	 snapshot.forEach(function(childSnapshot) {
    var key = childSnapshot.key();
    var childData = childSnapshot.val();
    if (childData>pos) {
    	fireB.child(key).set(childData-1);
    }

    fireB.child(user).set(null);
  });



	})

	var myFire = new Firebase('https://ummo.firebaseio.com/qEr/users/'+user+'/joinedQs')

	myFire.once("value",function (snap) {
			myFire.child(snap.numChildren()).set(null);
	});
}

function joinedQ(user,callback){
	var joinedQs = Array();
	var fireB = new Firebase('https://ummo.firebaseio.com/qMaster/users/');
	fireB.once("value",function (snap) {
		console.log("got Value");
		var arr = snap.val()
		for (var i in  arr) {
			console.log(arr[i].cellNum);
			if (arr[i].cellNum) {
				if(fireB.child(arr[i].cellNum).child("managedQ")){
					if (fireB.child(arr[i].cellNum).child("managedQ").child("qErs")) {
						if (snap.child(arr[i].cellNum).child("managedQ").child("qErs").child(user).exists()) {
								joinedQs.push(arr[i].managedQ);
						}
					}
				}
			}



		}
		//var obj={data:joinendQs}
		callback({joinedVQs:joinedQs});
		//console.log(obj);
		return;
	})
}

function allQsInProvider(provider,callback){
	var fireB = new Firebase('https://ummo.firebaseio.com/qMaster/users');
	fireB.once("value",function(snap){
		var qs = Array();
		for(var i in snap.val()){
			console.log(snap.val());
			if(snap.val()[i]){
			if (snap.val()[i].managedQ) {


			if(snap.val[i].managedQ.Provider){
			if(snap.val()[i].managedQ.Provider==provider){
				qs.push(snap.val());
			}
		}
	}}
	}
		callback(snap.val());
	})
}

function getCategories(callback){
		var fireB = new Firebase('https://ummo.firebaseio.com/Categories');
		clenQs("76583264",callback);
		fireB.once("value",function(snap){
			//callback(snap.val());
		})
}

exports.joinedQ=joinedQ;
exports.QEr=QEr;
exports.joinQ=joinQ;
exports.allQsInProvider = allQsInProvider;
exports.getCategories = getCategories;
