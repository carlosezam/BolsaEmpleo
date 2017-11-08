<?php

use yii\helpers\Html;
use yii\grid\GridView;
use yii\widgets\Pjax;
/* @var $this yii\web\View */
/* @var $searchModel app\models\EmpleoSearch */
/* @var $dataProvider yii\data\ActiveDataProvider */

$this->title = Yii::t('app', 'Empleos');
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="empleo-index">

    <h1><?= Html::encode($this->title) ?></h1>


    <?php
    // echo $this->render('_search', ['model' => $searchModel]);

    $model = new app\models\Empleo();

    \yii\bootstrap\Modal::begin([
        'header' => '<h2>Datos de la empresa</h2>',
        'toggleButton' => ['label'=>'Nuevo empleo', 'class' => 'btn btn-success']
    ]);

    echo $this->render('_form', ['model'=>$model]);

    \yii\bootstrap\Modal::end();

    ?>


<?php Pjax::begin(); ?>    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'filterModel' => $searchModel,
        'columns' => [
            ['class' => 'yii\grid\SerialColumn'],

            //'id',
            'puesto',
            'salario',
            'descripcion',
            'vacantes',
            // 'domicilio',
            // 'id_empresa',
            'id_municipio',
            // 'active',

            ['class' => 'yii\grid\ActionColumn',

            ],
        ],
    ]); ?>
<?php Pjax::end(); ?></div>
