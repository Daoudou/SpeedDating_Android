const express = require('express');
const router = express.Router();
const { validateBody } = require('./validation/route.validator');
const Infos = require('../models/infos.model')
const {body} = require('express-validator');

router.get('/infos', async(req,res)=>{
    const infos = await Infos.findAll()
    console.log(infos.every(info => info instanceof Infos))
    res.send(infos)
})

router.get('/infoId/:id',async (req,res)=>{
    const infos = await Infos.findOne({
        where:{
            id: req.params.id
        }
    })
    return res.status(200).send(infos)
})

router.post('/infosAdd',
    body('firstName'),
    body('lastName'),
    body('sexe'),
    async (req,res)=>{
    validateBody(req)
    try {
        const infos = await Infos.create({
            firstName: req.body.firstName,
            lastName: req.body.lastName,
            sexe: req.body.sexe,
            birthdate: req.body.birthdate
        })
        //console.log(infos.id)
        res.status(201)
        return res.send(infos.id).end()
    }catch (e) {
        console.error(e)
        res.status(409).send('Echec lors de l\'ajout')
    }
})


router.put('/:id',
    body('firstName').isString().notEmpty(),
    body('lastName').isString().notEmpty(),
    body('sexe').isString().notEmpty(),
    async (req,res)=>{
    try {
        await Infos.update({
            UserIdInfos: req.body.UserIdInfos,
            firstName: req.body.firstName,
            lastName: req.body.lastName,
            sexe: req.body.sexe,
            birthdate: req.body.birthdate
        },{
            where:{
                id: req.params.id
            }
        })
        res.status(201).send('Utilisateur mis a jour')
    }catch (e){
        console.error(e)
        res.status(409).send('Erreur lors de la mis a jour de l\'utilisateur')
    }
})



exports.initializeRoutes = () => router;