var express = require('express');
var expressHbs = require('express-handlebars');
const dangnhap = require('./models/userdangnhap');


var app = express();
// app.listen(2000);
{/* <link rel="stylesheet" type="text/css" href="https://drive.google.com/drive/folders/15XjZQ53Wf17wJkO6QeyUbHHZ6yOFV2Zm?usp=sharing"></link> */}
app.use(express.static('css'));
app.use(express.static('js'));
app.use(express.static('images'));


app.engine('.hbs', expressHbs.engine({ extname: '.hbs', defaultLayout: "main" }));
app.set('view engine', '.hbs');

app.get('/home', function(req, res){
    res.render('index');
});
app.get('/dangki', function (req, res) {
    res.render('dangki');
});


const mongoose = require('mongoose');
const userRouter = require('./routes/userRoutes');
const bodyParser = require('body-parser');


const userContronlers = require('./contronler/userContronlers');

const url = "mongodb+srv://thong2023:123456ok@cluster0.c943vqr.mongodb.net/Qlsinhvien?retryWrites=true&w=majority"



app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(bodyParser.json());
app.use(express.json());



mongoose.connect(url, { useUnifiedTopology: true, useNewUrlParser: true });

app.use(userRouter);
app.use('/thongtin', userContronlers);
app.use('/dangki',  userContronlers);
app.use('/dangki1', userContronlers);







app.listen(4000, () => {
    console.log('server is running');
})

