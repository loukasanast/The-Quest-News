module.exports = function(grunt){
    grunt.initConfig({
        connect: {
            server: {
                options: {
                    hostname: 'localhost',
                    port: 8080,
                    base: '.',
                    keepalive: true
                }
            }
        }
    });
    grunt.loadNpmTasks('grunt-contrib-connect');
    grunt.registerTask('default', ['connect']);
};