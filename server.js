const express = require('express');
const mongoose = require('mongoose');
// const userRouter = require('./routes/userRoutes');
const bodyParser = require('body-parser');
const exphdbs = require('express-handlebars')
const userContronlers = require('./contronler/userContronlers');

const url = "mongodb+srv://thong2023:123456ok@cluster0.c943vqr.mongodb.net/Qlsinhvien?retryWrites=true&w=majority"

const app = express();

app.use(bodyParser.urlencoded({
    extended:true
}));
app.use(bodyParser.json());
app.use(express.json());



mongoose.connect(url,{useUnifiedTopology:true, useNewUrlParser:true});

// app.use(userRouter);
app.use('/user', userContronlers);



app.listen(3000, ()=>{console.log('server is running');
})