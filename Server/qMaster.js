/***************************************************************************************************
*************************************UMMO QMASTER & RELATED FUNCTIONS*******************************
***************************************************************************************************/

function register(qMaster, callback) {
	var fireB = new Firebase('https://ummo.firebaseio.com/qMaster/users/' + qMaster.cellNum);
	fireB.set(qMaster);
}

function createQ(qMaster, vQ, callback) {
	var fireB = new Firebase('https://ummo.firebaseio.com/qMaster/users/' + qMaster.cellNum + "/managedQ");
	fireB.set(vQ);

}

function destroyQ(qMaster, callback) {
	var fireB = new Firebase('https://ummo.firebaseio.com/qMaster/users/' + qMaster.cellNum + "/managedQ");
	fireB.set(null);

}

function dQUser(qMaster, user) {
	console.log(user);
	var fireB = new Firebase('https://ummo.firebaseio.com/qMaster/users/' + qMaster.cellNum + "/managedQ/qErs");
	fireB.once("value", function (snapshot) {
		if(fireB.child(user)){
		var ttdq=new Firebase('https://ummo.firebaseio.com/qMaster/users/' + qMaster.cellNum + "/managedQ/ttdq");
		var	dqTime = new Firebase('https://ummo.firebaseio.com/qMaster/users/' + qMaster.cellNum + "/managedQ/");
		dqTime.once("value",function(snap){
			if (snap.child("dqTime").exists()) {
				console.log(snap.child("dqTime").val());
				dqTime.child("ttdq").set(Date.now()-snap.child("dqTime").val());
			}
				dqTime.child("dqTime").set(Date.now());

		});

	var pos=snapshot.child(user).val();
	snapshot.forEach(function(childSnapshot) {
	    var key = childSnapshot.key();
	    var childData = childSnapshot.val();
	    if (childData > pos) {
	    	fireB.child(key).set(childData-1);
	    }

    	fireB.child(user).set(null);
  	});
}
});
}

function moveUser(user,vQ) {
}

function getFeedbacks(vQ){

}

function getMyques(qMaster) {
}

function destroyAllQs() {

}

function update(qMaster,callback) {
	var fireB = new Firebase('https://ummo.firebaseio.com/qMaster/users/'+qMaster.cellNum);
	fireB.on("value",function (snapshot) {callback(snapshot.val())},function (err) {callback(err)});
}


exports.createQ = createQ;
exports.register = register;
exports.destroyQ = destroyQ;
exports.dQUser = dQUser;
exports.moveUser = moveUser;
exports.getFeedbacks = getFeedbacks;
exports.getMyques = getMyques;
exports.destroyAllQs = destroyAllQs;
exports.update = update;