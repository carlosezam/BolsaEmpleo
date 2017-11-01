<?php
return [
    'createEmpresa' => [
        'type' => 2,
        'description' => 'Registrar una empresa',
    ],
    'manageEmpresa' => [
        'type' => 2,
        'description' => 'Administar datos de una empresa',
    ],
    'manageEmpleo' => [
        'type' => 2,
        'description' => 'Administar datos de una empleo',
    ],
    'managePerson' => [
        'type' => 2,
        'description' => 'Administar datos personales',
    ],
    'empresa' => [
        'type' => 1,
        'children' => [
            'manageEmpresa',
            'manageEmpleo',
        ],
    ],
    'person' => [
        'type' => 1,
        'children' => [
            'managePerson',
        ],
    ],
    'admin' => [
        'type' => 1,
        'children' => [
            'createEmpresa',
            'empresa',
            'person',
        ],
    ],
];
