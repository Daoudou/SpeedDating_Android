const express = require('express');
const router = express.Router();
const link = require('../models/linkDateInfosUser.model')


router.get('/linkUser/:id', async(req,res)=>{
    const linkInfos = await link.findAll({
        where:{
            idUser: req.params.id
        }
    })
    return res.status(200).send(linkInfos)
})

router.get('/linkInfos/:id', async(req,res)=>{
    const linkInfos = await link.findAll({
        where:{
            idInfos: req.params.id
        }
    })
    return res.status(200).send(linkInfos)
})

exports.initializeRoutes = () => router;