Firebase = require("firebase");
function QEr(user,callback){
	var fireB = new Firebase('https://ummo.firebaseio.com/qEr/users/'+user.cellNum);

	fireB.set(user);
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

function allQs(callback){
	var qs = Array();
	var fireB = new Firebase('https://ummo.firebaseio.com/qMaster/users/');
	fireB.once("value",function (snap) {
		for(var i in snap.val()){
			qs.push(snap.val()[i]);
		}
		
		callback(qs);
	});
}

exports.joinedQ=joinedQ;
exports.QEr=QEr;
exports.joinQ=joinQ;
exports.allQs = allQs;
