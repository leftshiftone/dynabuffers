const path = require('path');

module.exports = (env, argv) => ({
    entry: {
        'dynabuffers' : './dynabuffers/all.ts'
    },
    module: {
        rules: [
            {
                test: /\.ts?$/,
                use: 'ts-loader',
                exclude: /node_modules/,
            },
        ],
    },
    resolve: {
        extensions: ['.ts', '.js'],
    },
    output: {
        library:"Dynabuffers",
        libraryTarget: "umd",
        filename: 'libs/[name].min.js',
        path: path.resolve(__dirname, 'dist'),
    }
});
