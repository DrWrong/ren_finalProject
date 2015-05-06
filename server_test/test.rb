#!/usr/bin/env ruby
$:.push("gen-rb")

require "thrift"
require "ontology_service"

port = 9090

transport = Thrift::Socket.new('localhost', port)
protocol = Thrift::BinaryProtocol.new(transport)
client = OntologyService::Client.new(protocol)
transport.open()


res = client.getInitValues()

puts res.exTempl
puts res.exPH2
puts res.exH2s
puts res.exOxy2
puts res.exampleConducitivity2

res.exOxy2 = 2
res2 = client.perfomReason(res)

puts res2.reportH2S
puts res2.reportGQOW
puts res2.reportOil
puts res2.verifyData
