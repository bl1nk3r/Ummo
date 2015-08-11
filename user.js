Firebase = require("firebase");
function QEr(user,callback){
	var fireB = new Firebase('https://ummo.firebaseio.com/qEr/users/'+user.cellNum);
	
	fireB.set(user);
}

function joinQ(user,vq,callback) {
	
	var fireB = new Firebase('https://ummo.firebaseio.com/qMaster/users/'+vq+'/managedQ/qErs');
	
	fireB.once("value",function (snap) {
		if (snap.child(user).exists()) {
			console.log("Already Registered");
		}
		else {
		fireB.child(user).set(snap.numChildren());
	}
	})
	
	var myFire = new Firebase('https://ummo.firebaseio.com/qEr/users/'+user+'/joinedQs')
	
	myFire.once("value",function (snap) {
			myFire.child(snap.numChildren()).set(vq);
	});
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

function joinedQ(user,vq){
	var fireB = new Firebase('https://ummo.firebaseio.com/qMaster/users/'+vq+"/managedQ/qErs/"+user);
	fireB.once("value",function (snap) {
		console.log("got Value");
		console.log(snap.val());
		return;
	})
}

exports.joinedQ=joinedQ;
exports.QEr=QEr;
exports.joinQ=joinQ;