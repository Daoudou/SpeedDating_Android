const express = require('express');
const router = express.Router();
const uuid = require('uuid')
const Infos = require('../models/infos.model')
const linkInfosDateUser = require('../models/linkDateInfosUser.model')
const {body} = require('express-validator');
const jwtDecode = require("jwt-decode");

router.get('/infos', async(req,res)=>{
    const infos = await Infos.findAll()
    console.log(infos.every(info => info instanceof Infos))
    res.send(infos)
})

router.get('/infoId/:id',async (req,res)=>{
    const infos = await Infos.findAll({
        where:{
            id: req.params.id
        }
    })
    return res.status(200).send(infos)
})

router.post('/infosAdd/:firstName/:lastName/:sexe/:birthdate/:idUserAdd',
    async (req,res)=>{
    try {
        const idlers = uuid.v4()

        const personne = await Infos.findOne({
            where : {
                firstName: req.params.firstName,
                lastName: req.params.lastName,
                sexe: req.params.sexe,
                birthdate: req.params.birthdate
            }
        })

        if (personne == null) {
            await Infos.create({
                id: idlers,
                firstName: req.params.firstName,
                lastName: req.params.lastName,
                sexe: req.params.sexe,
                birthdate: req.params.birthdate
            })
            await linkInfosDateUser.create({
                idUser: req.params.idUserAdd,
                idInfos: idlers
            })
            res.status(201).send("Ajout effectuer")
        }else{
            await linkInfosDateUser.create({
                idUser: req.params.idUserAdd,
                idInfos: personne.id
            })
            res.status(201).send("Personne existante")
        }

    }catch (e) {
        console.log(e)
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