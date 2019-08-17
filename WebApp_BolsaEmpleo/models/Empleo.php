<?php

namespace app\models;

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
 *
 * @property Empresas $idEmpresa
 */
class Empleo extends \yii\db\ActiveRecord
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
            [
                ['puesto', 'salario', 'descripcion' ,'vacantes', 'id_empresa', 'id_municipio'],
                    'required', 'message' => 'Campo {attribute} requerido'
            ],
            [
                ['salario'],
                    'number',   'message'=> 'El salario debe ser un número',
                    'min' => 1, 'tooSmall' => 'Ingrese una cantidad positiva'
            ],
            [
                ['vacantes'],
                    'integer', 'message' => 'El numero de vacantes dede ser un entero',
                    'min' => 0, 'tooSmall' => 'el numero de vacantes debe ser positivo'
            ],
            [
                ['puesto'],
                    'string', 'message' => 'El puesto debe ser un texto',
                    'length' => [5,50],
                        'tooShort' => 'Al menos 5 caracteres',
                        'tooLong' => 'Maximo 50 caracteres'
            ],
            [
                ['active'], 'boolean'
            ],
            [
                ['descripcion', 'domicilio'],
                    'string', 'max' => 150
            ],
            [
                ['id_empresa'],
                    'exist', 'skipOnError' => true,
                    'targetClass' => Empresa::className(), 'targetAttribute' => ['id_empresa' => 'id']
            ],
            [
                ['id_municipio'],
                'exist', 'skipOnError' => true,
                'targetClass' => Municipios::className(), 'targetAttribute' => ['id_municipio' => 'id']
            ],
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
            'active' => 'Disponible',
            'id_empresa' => 'Empresa',
            'id_municipio' => 'Municipio',
        ];


    }

    /**
     * @return \yii\db\ActiveQuery
     */
    public function getEmpresa()
    {
        return $this->hasOne(Empresa::className(), ['id' => 'id_empresa']);
    }

    public function getMunicipio()
    {
        return $this->hasOne(Municipios::className(), ['id' => 'id_municipio']);
    }
}
