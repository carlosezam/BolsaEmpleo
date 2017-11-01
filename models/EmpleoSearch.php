<?php

namespace app\models;

use Yii;
use yii\base\Model;
use yii\data\ActiveDataProvider;
use app\models\Empleo;

/**
 * EmpleoSearch represents the model behind the search form about `app\models\Empleo`.
 */
class EmpleoSearch extends Empleo
{
    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['id', 'vacantes', 'id_empresa'], 'integer'],
            [['puesto', 'descripcion', 'domicilio'], 'safe'],
            [['salario'], 'number'],
        ];
    }

    /**
     * @inheritdoc
     */
    public function scenarios()
    {
        // bypass scenarios() implementation in the parent class
        return Model::scenarios();
    }

    /**
     * Creates data provider instance with search query applied
     *
     * @param array $params
     *
     * @return ActiveDataProvider
     */
    public function search($params)
    {
        $query = Empleo::find();

        // add conditions that should always apply here

        $dataProvider = new ActiveDataProvider([
            'query' => $query,
        ]);

        $this->load($params);

        if (!$this->validate()) {
            // uncomment the following line if you do not want to return any records when validation fails
            // $query->where('0=1');
            return $dataProvider;
        }


        // grid filtering conditions
        $query->orFilterWhere([
            'id' => $this->id,
            'salario' => $this->salario,
            'vacantes' => $this->vacantes,
            'id_empresa' => $this->id_empresa,
        ]);


        $query->orFilterWhere(['like', 'puesto', $this->puesto])
            ->orFilterWhere(['like', 'descripcion', $this->descripcion])
            ->orFilterWhere(['like', 'domicilio', $this->domicilio]);

        return $dataProvider;
    }
}
