<?php

namespace app\models\api;

use Yii;
use yii\base\Model;
use yii\data\ActiveDataProvider;
use app\models\api\Empleos;

/**
 * EmpleosSearch represents the model behind the search form about `app\models\api\Empleos`.
 */
class EmpleosSearch extends Empleos
{
    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['id', 'vacantes', 'id_empresa', 'id_municipio', 'active'], 'integer'],
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
        $query = Empleos::find();

        $dataProvider = new ActiveDataProvider([
            'query' => $query,
        ]);

        $this->load($params);

        if (!$this->validate()) {
            // uncomment the following line if you do not want to return any records when validation fails
            // $query->where('0=1');
            return $dataProvider;
        }

        $query->andFilterWhere([
            'id' => $this->id,
            'salario' => $this->salario,
            'vacantes' => $this->vacantes,
            'id_empresa' => $this->id_empresa,
            'id_municipio' => $this->id_municipio,
            'active' => $this->active,
        ]);

        $query->andFilterWhere(['like', 'puesto', $this->puesto])
            ->andFilterWhere(['like', 'descripcion', $this->descripcion])
            ->andFilterWhere(['like', 'domicilio', $this->domicilio]);

        return $dataProvider;
    }
}
