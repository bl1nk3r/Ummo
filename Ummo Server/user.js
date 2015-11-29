Firebase = require("firebase");
function QEr(user,callback){
	var fireB = new Firebase('https://ummo.firebaseio.com/qEr/users/'+user.cellNum);

	fireB.set(user);
}

function removeQs(vq,user,callback){
	var found = 0;
	var last=0;
	var fireBQs = new Firebase('https://ummo.firebaseio.com/qEr/users/'+user+"/joinedQs");
	fireBQs.once("value",function(snap){
		snap.forEach(function(childSnapshot){
			if((childSnapshot.val()==vq)){
				found=childSnapshot.key();
			}
			last = childSnapshot.key();

		})
			console.log("Removing"+found+"Last one is"+last);
			var arr = snap.val();
			//console.log(arr[2]);
			for(var i=Number(found);i<last;i++){
				if(i<last){
					fireBQs.child(i).set(arr[i+1]);
					console.log(arr[i+1]);
					console.log(i+1);
				}
				else {
						console.log("Removing");
						fireBQs.child(i).set(null);
				}
			}
			if(found>0){
				fireBQs.child(last).set(null);
			}
			console.log(found);
			if(found==0){
					fireBQs.child(found).set(null);
			}

	});
	callback("Helo");
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
							if(snapshot.key()==childSnapshot.val()){
								console.log(childSnapshot.key()+":"+snapshot.key());
								fireBQs.child(childSnapshot.key()).set(null);
								for(var j = childSnapshot.key();j<snap.numChildren();j++){
									console.log()
								}
							}

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

function leaveQ(user,vq,callback) {

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

/*	myFire.once("value",function (snap) {
			myFire.child(snap.numChildren()).set(null);
	});
*/
	removeQs(vq,user,callback);
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
		fireB.once("value",function(snap){
			callback(snap.val());
		})
}

function getQ(vq,callback){
	var fireB = new Firebase('https://ummo.firebaseio.com/qMaster/users/'+vq+'/managedQ');
	fireB.once('value',function(snap){
		callback(snap.val());
	})
}
exports.getQ=getQ;
exports.leaveQ = leaveQ;
exports.removeQs = removeQs;
exports.joinedQ=joinedQ;
exports.QEr=QEr;
exports.joinQ=joinQ;
exports.allQsInProvider = allQsInProvider;
exports.getCategories = getCategories;
