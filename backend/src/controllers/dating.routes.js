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

router.get('/userDateId/:id', async (req, res) => {
    const dateById = await Dating.findAll({
        where: {
            idUserDating: req.params.id
        }
    })
    //
    // const temp = {
    //     id: dateById.id
    // }
    console.log(dateById)
    let tab = []
    for (const date of dateById) {
        const temp = {
            id: date.id,
            idUserDating: date.idUserDating,
            idInfosDating: date.idInfosDating,
            peopleAdding: date.peopleAdding,
            dateDating: date.dateDating.toLocaleDateString(),
            comment: date.comment,
            note: date.note
        }
        tab.push(temp)
    }
    res.send(tab)
})

router.get('/infosDatingId/:id', async (req, res) => {
    const dateId = await Dating.findAll({
        where: {
            idInfosDating: req.params.id
        }
    })
    res.send(JSON.stringify(dateId))
})

router.post('/datingAdd/:peopleAddingName/:dateDating/:comment/:note/:InfoAddId/:idUser',
    async (req, res) => {
        try {
            await Dating.create({
                idUserDating: req.params.idUser,
                idInfosDating: req.params.InfoAddId,
                peopleAdding: req.params.peopleAddingName,
                dateDating: req.params.dateDating,
                comment: req.params.comment,
                note: parseInt(req.params.note),
            })
            res.status(201).send('Infos de la rencontre ajouter').end()
        } catch (e) {
            console.error(e)
            res.status(400).send('Erreur lors des infos sur les rencontres')
        }
    })


router.put('/:id/:dateDating/:note/:comment',
    async (req, res) => {
        try {
            await Dating.update({
                dateDating: req.params.dateDating,
                comment: req.params.comment,
                note: parseInt(req.params.note),
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