const express = require('express');
const userModel = require('../models/user');
const dangnhap = require('../models/userdangnhap');
const bodyParser = require('body-parser');

const app = express();
{/* <link rel="stylesheet" type="text/css" href="css/animate.min.css"></link> */}
app.use(express.static('css'));
app.use(express.static('js'));
app.use(express.static('images'));
const mongoose = require('mongoose');
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(bodyParser.json());
const url = "mongodb+srv://thong2023:123456ok@cluster0.c943vqr.mongodb.net/Qlsinhvien?retryWrites=true&w=majority"
mongoose.connect(url, { useUnifiedTopology: true, useNewUrlParser: true });







app.get('/', (req, res) => {
    res.render('thongtin.hbs'
        ,
        {
            viewTitle: "Nhập thông tin"
        });
});
app.post('/add', async (req, res) => {

    console.log(req.body);
    if(req.body.id == ''){
        
            addRecord(req, res);
        
        
    }else{
        updateRecord(req, res);

    }
    
});
function addRecord(req, res){
    const { Masv, Tensv, Diem, Sdt, Anh} = req.body;

    if (!Masv || !Tensv || !Diem || !Sdt || !Anh) {
        // Trả về lỗi nếu thiếu thông tin
        res.render('thongtin.hbs',
            {
                viewTitle1: "Thêm thất bại. Vui lòng nhập đầy đủ thông tin"
            });
        return;
    }
    const u = new userModel(req.body);
    try {  
            u.save();
            // res.send(u);
            res.render('thongtin.hbs',
                {
                    viewTitle: "Thêm thành công "
                });
    } catch (error) {
        res.status(500).send(error);

    }
     
}
function updateRecord(req, res) {
    userModel.findByIdAndUpdate(req.body.id, req.body, { new: true })
        .then((doc) => {
            res.redirect('/thongtin/listdulieu' );
        })
        .catch((err) => {
            console.log(err);
            res.render('thongtin.hbs', {
                viewTitle: "ERROR UPDATE"
            });
        });
}


app.get('', (req, res) => {
    res.render('dangki.hbs');
})
//dangnhap
app.post('/dangnhaps', (req, res, next) => {

    var TenDn = req.body.tendn;
    var Matkhau = req.body.mk;

    dangnhap.findOne({
        TenDn: TenDn, 
        Matkhau: Matkhau
        
    })
        .then(data => {
            if (data) {
                
                res.render('index.hbs');
                
                    
            } else {
            
                res.render('dangki.hbs',
                    {
                        viewTitle: "Tài khoản hoặc mật khẩu sai"



                    });
            }
        })
        .catch(err => {
            console.error(err);
            res.status(500).json('lỗi server')
            
        });
});
//dangki
app.get('/thong', (req, res) => {
    res.render('dangki1.hbs');
   
});
app.post('/dangkis', async (req, res) => {
    const { TenDn, Matkhau} = req.body;

    if (!TenDn || !Matkhau ) {
        // Trả về lỗi nếu thiếu thông tin
        res.render('dangki1.hbs',
            {
                viewTitle: "Đăng kí thất bại. Vui lòng nhập đầy đủ thông tin"
            }
           );
        return;
    }

    const u = new dangnhap(req.body);
    try {
        await u.save();
        res.render('dangki.hbs')
        


    } catch (error) {
        res.status(500).send(error);

    }
});
//đây du lieu len
// app.get('/listdulieu', (req, res) => {
//     res.render('danhsach.hbs',
//     {
//         viewTitle2:"Tải dữ liệu thành công"
//     });
// });
app.get('/listdulieu', (req, res) => {
    userModel.find({}).then(users =>{
        res.render('danhsach.hbs',
            {
                users: users.map(user =>user.toJSON())
            });

    })
   
});
app.get('/edit/:id', async (req, res) => {
    try {
        const user = await userModel.findById(req.params.id).lean();
        res.render('thongtin.hbs', {user});
    } catch (err) {
        console.log(err);
        res.redirect('/');
    }
});
//delete
app.get('/delete/:id', async (req, res) => {
        try {
            const user = await userModel.findByIdAndDelete(req.params.id, req.body);
            if (!user) res.status(404).send("No item found");
            else {
                res.redirect('/thongtin/listdulieu');
            }
            //res.status(200).send();
        } catch (error) {
            res.status(500).send(error);
        }
});

module.exports = app;