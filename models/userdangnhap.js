const mongoose = require('mongoose');
const UserSchema1 = new mongoose.Schema({
    TenDn: {
        type: String
    },
    Matkhau: {
        type: String
    }

});
const dangnhap = mongoose.model('dangnhap', UserSchema1);
module.exports = dangnhap;