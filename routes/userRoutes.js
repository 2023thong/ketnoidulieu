const express = require('express');
const userModel = require('../models/user');
const userModel1 = require('../models/userdangnhap');
const app = express();



//add data
app.post('/user',  async (req, res) =>{
    const u = new userModel(req.body);
    try {
        await u.save();
        res.send(u);  
    } catch (error) {
        res.status(500).send(error);
        
    }
});
app.get('/list', async (req, res) => {
    const users = await userModel.find({});
    try {
        res.send(users)
    } catch (error) {
        res.status(500).send(error);

    }

});
app.post('/dangnhaps1', async (req, res) => {
    const u1 = new userModel1(req.body);
    try {
        await u1.save();
        res.send(u1);
    } catch (error) {
        res.status(500).send(error);

    }
});
app.get('/listdangnhap', async (req, res) => {
    const dangnhaps = await userModel1.find({});
    try {
        res.send(dangnhaps);

    } catch (error) {
        res.status(500).send(error);

    }

});


app.patch('/user/:id', async(req, res) =>{
    try {
        const user = await userModel.findByIdAndUpdate(req.params.id, req.body);
        await userModel.save();
        res.send(user)
        
    } catch (error) {
        res.status(500).send(error);
        
    }

});
app.delete('/user/:id', async (req, res) => {
    try {
        const user = await userModel.findByIdAndDelete(req.params.id, req.body);
        if(!user) res.status(404).send("No item found");
        res.status(200).send();

    } catch (error) {
        res.status(500).send(error);

    }

});


module.exports = app;