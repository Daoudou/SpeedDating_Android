const express = require('express');
const router = express.Router();
const Dating = require('../models/dating.model')
const {body} = require('express-validator');
const {validateBody} = require("./validation/route.validator");
const jwtdecode = require('jwt-decode')

router.get('/', async (req, res) => {
    try {
        const dating = await Dating.findAll()
        console.log(dating.every(user => user instanceof Dating))
        res.send(dating)
        res.status(200)
    } catch (e) {
        res.status(500)
    }
})

router.get('/:id', async (req, res) => {
    const dateIdUser = await Dating.findAll({
        where: {
            id: req.params.id
        }
    })
    res.send(dateIdUser)
})

router.get('/userDateId', async (req,res)=>{
    const token = jwtdecode(req.headers.authorization)
    const dateById = await Dating.findAll({
        where:{
            UserId: token.id
        }
    })
    res.send(dateById)
})

router.get('/:idRencontre', async (req, res) => {
    const dateId = await Dating.findAll({
        where: {
            id: req.params.idRencontre
        }
    })
    res.send(JSON.stringify(dateId))
})

router.post('/datingAdd',
    //body('UserId').isString().notEmpty(),
    body('InfoId').isString().notEmpty(),
    body('dateDating').isString().notEmpty(),
    body('note').isInt(),
    async (req, res) => {
        validateBody(req)
        try {
            const token = jwtdecode(req.headers.authorization)
            await Dating.create({
                UserId: token.id,
                InfoId: req.body.InfoId,
                dateDating: req.body.dateDating,
                comment: req.body.comment,
                note: parseInt(req.body.note),
            })
            res.status(201).send('Infos de la rencontre ajouter').end()
        } catch (e) {
            console.error(e)
            res.status(400).send('Erreur lors des infos sur les rencontres')
        }
    })


router.put('/:id',
    body('dateDating').isString().notEmpty(),
    body('note').isString().notEmpty(),
    async (req, res) => {
        try {
            await Dating.update({
                dateDating: req.body.dateDating,
                comment: req.body.comment,
                note: parseInt(req.body.note),
            }, {
                where: {
                    id: req.params.id
                }
            })
            res.status(200).send('Rencontre mise a jour')

        } catch (e) {
            console.error(e)
            res.status(400).send('Erreur lors de la mise a jour de la rencontre')
        }
    })

router.delete('/deleteDate/:id', async (req, res) => {
    try {
        await Dating.destroy({
            where: {
                id: req.params.id
            }
        })
        res.status(200)
        res.send('Rencontre Supprimée')
    } catch (e) {
        console.error(e)
        res.status(400).send('Erreur lors de la suppression')
    }
})


exports.initializeRoutes = () => router;