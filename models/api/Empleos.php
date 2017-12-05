<?php

namespace app\models\api;

use app\models\Empresa;
use app\models\Municipios;
use Yii;

/**
 * This is the model class for table "empleos".
 *
 * @property integer $id
 * @property string $puesto
 * @property double $salario
 * @property string $descripcion
 * @property integer $vacantes
 * @property string $domicilio
 * @property integer $id_empresa
 * @property integer $id_municipio
 * @property integer $active
 *
 * @property Empresas $idEmpresa
 * @property Municipios $idMunicipio
 * @property Saves[] $saves
 */
class Empleos extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'empleos';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['puesto', 'salario', 'descripcion', 'vacantes', 'id_empresa', 'id_municipio'], 'required', 'required', 'message' => 'Campo {attribute} requerido'],
            [['salario'], 'number'],
            [['vacantes', 'id_empresa', 'id_municipio', 'active'], 'integer'],
            [['puesto'], 'string', 'max' => 50],
            [['descripcion', 'domicilio'], 'string', 'max' => 150],
            [['id_empresa'], 'exist', 'skipOnError' => true, 'targetClass' => Empresa::className(), 'targetAttribute' => ['id_empresa' => 'id']],
            [['id_municipio'], 'exist', 'skipOnError' => true, 'targetClass' => Municipios::className(), 'targetAttribute' => ['id_municipio' => 'id']],
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'puesto' => 'Puesto',
            'salario' => 'Salario',
            'descripcion' => 'Descripcion',
            'vacantes' => 'Vacantes',
            'domicilio' => 'Domicilio',
            'id_empresa' => 'Id Empresa',
            'id_municipio' => 'Id Municipio',
            'active' => 'Active',
        ];
    }

    public function fields()
    {
        return [
            'id' => 'id',
            'puesto' => 'puesto',
            'salario' => 'salario',
            'descripcion' => 'descripcion',
            'vacantes' => 'vacantes',
            'domicilio' => 'domicilio',
            'empresa' => function(){ return $this->empresa->nombre; },
            'municipio' => function(){ return $this->municipio->nombre; },
            'contacto' => function(){ return $this->empresa->telefono; },
            'active' => 'active',
        ];
    }


    /**
     * @return \yii\db\ActiveQuery
     */
    public function getEmpresa()
    {
        return $this->hasOne(Empresa::className(), ['id' => 'id_empresa']);
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getMunicipio()
    {
        return $this->hasOne(Municipios::className(), ['id' => 'id_municipio']);
    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getSaves()
    {
        return $this->hasMany(Saves::className(), ['id_empleo' => 'id']);
    }
}
