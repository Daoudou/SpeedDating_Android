const express = require('express')
const router = express.Router()
const userAdmin = require('../models/db')
const User = require('../models/user.model')
const {body} = require('express-validator');
const {validateBody} = require("./validation/route.validator");
const jwt = require("jsonwebtoken");
var bcrypt = require('bcryptjs')

router.post('/login', body('email').notEmpty(), body('password'), async (req, res) => {
    validateBody(req)
    const userAdminLogin = await User.findOne({
        where: {
            email: req.body.email
        }
    })

    if (!userAdminLogin) {
        throw new Error('Admin user not found')

    }

    const passwordAdminValid = bcrypt.compareSync(req.body.password, userAdminLogin.password)
    if (passwordAdminValid) {
        const token = jwt.sign(
            {id: userAdminLogin.id, email: userAdminLogin.email, password: userAdminLogin.password},
            'abcdefghijklmnoqrstuvxyzABSCDEFGHIJKLMNOPQRSTUVWXYZ'
        )
        res.send('Login Admin success\n' + userAdminLogin.firstName + '\n' + userAdminLogin.email + '\n' + token)
    } else {
        res.status(400).send('Password admin invalid')
    }
})

exports.initializeRoutes = () => router