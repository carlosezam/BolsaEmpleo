<?php

/* @var $this yii\web\View */
use yii\helpers\Html;
use yii\helpers\Url;
$this->title = 'My Yii Application';
?>
<style>
    .site-index {
    }

    .site-index::after {
        content: "";
        background: url("<?=Url::to('@web/images/logo.jpg')?>");
        opacity: 0.1;
        top: 0;
        left: 0;
        bottom: 0;
        right: 0;
        position: absolute;
        z-index: -1;

        background-position: center center;
        background-repeat: no-repeat;
        background-attachment: fixed;
        background-size: cover;
        min-height: 100%;
        -webkit-background-size: cover;
        -moz-background-size: cover;
        width: 100%;
    }
</style>
<div class="site-index">


    <div class="jumbotron">
        <h1>BOLSA DE EMPLEO</h1>
    </div>

    <div id="bc" class="body-content">
        <div class="row">
            <div class="col-sm-12">
                <h2>MISIÓN</h2>
                <p>
                    Contribuir a través de servicios de información y orientación laboral; de apoyos económicos para la capacitación, el empleo y el autoempleo;
                    y de acciones de movilidad laboral;
                    a mejorar las oportunidades laborales de la población desempleada y subempleada del estado para su efectiva incorporación al mercado laboral.
                </p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12">
                <h2>VISIÓN</h2>
                <p>
                    Ser la Dependencia rectora de la política laboral que promueva el trabajo digno y la justicia social; mediante la capacitación y adiestramiento que impulse el empleo y autoempleo, así como, acciones de prevención, conciliación y defensa en materia laboral; con probidad, humanismo, eficacia, eficiencia y transparencia; en beneficio de la población chiapaneca.
                </p>
            </div>
        </div>
    </div>
</div>
