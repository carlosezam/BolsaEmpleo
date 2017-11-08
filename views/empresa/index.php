<?php

use yii\helpers\Html;
use yii\grid\GridView;
use yii\bootstrap\Modal;
use yii\widgets\Pjax;

/* @var $this yii\web\View */
/* @var $searchModel app\models\EmpresaSearch */
/* @var $dataProvider yii\data\ActiveDataProvider */

$this->title = Yii::t('app', 'Empresas');
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="empresa-index">

    <h1><?= Html::encode($this->title) ?></h1>
    <?php //echo $this->render('_search', ['model' => $searchModel]); ?>

    <?php
        $model_empresa = new app\models\Empresa();

        Modal::begin([
            'header' => '<h2>Datos de la empresa</h2>',
            'toggleButton' => ['label' => 'Nueva empresa', 'class' => 'btn btn-success']
        ]);

        echo $this->render('_form', ['model'=>$model_empresa]);

        Modal::end();


    ?>
    <p>
        <?php //echo Html::a(Yii::t('app', 'Create Empresa'), ['create'], ['class' => 'btn btn-success']) ?>
    </p>
    <?php
    Pjax::begin();
    echo GridView::widget([
        'layout' => "{items}\n{pager}",
        'dataProvider' => $dataProvider,
        'filterModel' => $searchModel,
        'columns' => [
            ['class' => 'yii\grid\SerialColumn'],

            //'id',
            //'rfc',
            'nombre',
            'telefono',
            'correo',
            // 'encargado',
            'domicilio',

            ['class' => 'yii\grid\ActionColumn'],

        ],

    ]);
    Pjax::end();
    ?>
</div>


<?php
/*
['class' => 'yii\grid\ActionColumn',
    'buttons' => [
        'update' => function ($url, $model, $key) {
            //Html::button('Update',['type'=>'button','class'])
            return Html::a('Update', '#w0', ['data-toggle'=>'modal','data-target'=>'#w0']);

        },
    ]
],*/

//<button type="button" class="btn btn-success" data-toggle="modal" data-target="#w0">Nueva empresa</button>
//<?php echo Html::a(Yii::t('app', 'Caancelar'), ['index'], ['class' => 'btn btn-danger'])
//<a href="/bolsa/web/empresa/update?id=1" title="Update" aria-label="Update" data-pjax="0"></a>
?>