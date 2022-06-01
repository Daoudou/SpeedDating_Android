const express = require('express');
const {body} = require('express-validator');
const router = express.Router();
const {validateBody} = require('./validation/route.validator');
const User = require('.././models/user.model')
var bcrypt = require('bcryptjs')
const jwt = require("jsonwebtoken");
var salt = bcrypt.genSaltSync(12)


router.get('/', async (req, res) => {
    const users = await User.findAll()
    console.log(users.every(user => user instanceof User))
    res.send(users)
});


router.get('/usersId/:id', async (req, res) => {

    const foundUser = await User.findOne({
        where: {
            id: req.params.id
        }
    })
    if (!foundUser) {
        throw new Error('User not found');
    }
    res.status(200).send(foundUser);
});

router.post('/login/:email/:password',
   /* body('email').notEmpty(),
    body('password').notEmpty(), */
    async (req, res) => {

    try {
        validateBody(req)
        const userLogin = await User.findOne({
            where: {
                email: req.params.email
            }
        })
        if (!userLogin) {
           return res.status(400).end()
        }else{
            const passwordVALID = bcrypt.compareSync(req.params.password, userLogin.password)
            if (passwordVALID) {
                const token = jwt.sign(
                    {id: userLogin.id, email: userLogin.email, password: userLogin.password},
                    'Cettenaufragéen\'entourepaslaraisonduplusfort.'
                )
                return res.end(JSON.stringify(token))
            } else {
              return res.status(401).send('password invalid').end()
            }
        }
    }catch (e) {
        console.error(e)
        return {error: 'Login erreur'}
    }
})

router.post(
    '/create/:pseudo/:email/:password',
    /*body('pseudo').notEmpty().isLength({min: 4}),
    body('email').notEmpty(),
    body('password').notEmpty().isLength({min: 5}),*/
    async (req, res) => {
        try {
            validateBody(req);
            const utilisateur = await User.create({
                pseudo: req.params.pseudo,
                email: req.params.email,
                password: bcrypt.hashSync(req.params.password, salt),
                roles: 'MEMBER'
            });
            console.log(utilisateur.id);
            res.status(201).send('Utilisateur creer').end();
        } catch (e) {
            console.error(e)
            res.status(409).send('Echec de la creation de l\'utilisateur')
        }
    }
);

router.put('/:id', async (req, res) => {
    await User.update({
        pseudo: req.body.pseudo,
        password: req.body.password,
    }, {
        where: {
            id: req.params.id
        }
    })
    res.status(204).end();
});

router.delete('/:id', async (req, res) => {
    await User.destroy({
        where: {
            id: req.params.id
        }
    })
    res.status(204).end();
});

exports.initializeRoutes = () => router;
