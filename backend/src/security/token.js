const {sign} = require('jsonwebtoken')

exports.generateAuthToken = (userId, email , password) =>{
    return sign({
        userId,
        email,
        password
    },'abcdefghijklmnoqrstuvxyzABSCDEFGHIJKLMNOPQRSTUVWXYZ',
        {expiresIn: 60 * 60 })
}