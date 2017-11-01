<?php

use yii\helpers\Html;
use yii\grid\GridView;
use yii\bootstrap\Modal;
/* @var $this yii\web\View */
/* @var $searchModel app\models\EmpleoSearch */
/* @var $dataProvider yii\data\ActiveDataProvider */

$this->title = Yii::t('app', 'Empleos');
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="empleo-index">

    <h1><?= Html::encode($this->title) ?></h1>
    <?php // echo $this->render('_search', ['model' => $searchModel]); ?>

    <?php
        $model_empleo = new app\models\Empleo();

        Modal::begin([
            'header' => '<h2>Datos del empleo</h2>',
            'toggleButton' => ['label' => 'Nueva empleo', 'class' => 'btn btn-success']
        ]);

        echo $this->render('_form', ['model'=>$model_empleo]);

        Modal::end();

    ?>

    <p>
        <?php // Html::a(Yii::t('app', 'Create Empleo'), ['create'], ['class' => 'btn btn-success'])
        ?>
    </p>
    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'filterModel' => $searchModel,
        'columns' => [
            ['class' => 'yii\grid\SerialColumn'],

            //'id',
            'puesto',
            'salario',
            'descripcion',
            //'vacantes',
            'domicilio',
            // 'id_empresa',

            ['class' => 'yii\grid\ActionColumn'],
        ],
    ]); ?>
</div>
