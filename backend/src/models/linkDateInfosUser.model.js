const {Sequelize, DataTypes, Model} = require('sequelize')
const { sequelize } = require('./db')
const Dating = require('../models/dating.model')
const User = require('./user.model')
const Infos = require('./infos.model')

const Link = sequelize.define('LinkDateInfoUser',{

    id: {
        type: DataTypes.UUID,
        defaultValue: DataTypes.UUIDV4,
        primaryKey: true
    },
    idUser: {
        type: DataTypes.UUID,
        defaultValue: DataTypes.UUIDV4,

        references : {
            model : User,
            key: 'id'
        }
    },
    idInfos: {
        type : DataTypes.UUID,
        defaultValue: DataTypes.UUIDV4,
        references: {
            model : Infos,
            key : 'id'
        }
    }
})

module.exports = Link