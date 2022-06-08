const {Sequelize, DataTypes, Model} = require('sequelize')
const { sequelize } = require('./db')
const User = require("./user.model");
const Infos = sequelize.define('Info',{
    id: {
        type: DataTypes.UUID,
        defaultValue: DataTypes.UUIDV4,
        primaryKey: true
    },
    idUser:{
        type: DataTypes.UUID,
        defaultValue: DataTypes.UUIDV4,
        references : {
            model : User,
            key: 'id'
        }
    },
    firstName: {
        type: DataTypes.STRING,
        allowNull: false
    },
    lastName: {
        type: DataTypes.STRING,
        allowNull: false
    },
    sexe: {
        type: DataTypes.STRING,
        allowNull: false
    },
    birthdate:{
        type: DataTypes.DATE,
        allowNull: false
    }
})

module.exports = Infos
