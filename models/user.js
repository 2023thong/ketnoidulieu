const mongoose = require('mongoose');
const UserSchema = new mongoose.Schema({
    Masv:{
        type: String
    },
    Tensv:{
        type:String
    },
    Diem:{
        type:String
    },
    Sdt:{
        type: String   
    },
    Anh:{
        type: String 

    }
    

});
const User = mongoose.model('User', UserSchema);
module.exports = User;