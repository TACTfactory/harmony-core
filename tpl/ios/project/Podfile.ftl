<#include utilityPath + "all_imports.ftl" />
# Uncomment this line to define a global platform for your project
# platform :ios, '8.0'
# Uncomment this line if you're using Swift
# use_frameworks!

target '${project_name}' do

    pod 'FMDB', '~> 2.5'
    pod 'AFNetworking', '~> 2.5.1'

end

target '${project_name}Tests' do

    pod 'Nocilla'

end
