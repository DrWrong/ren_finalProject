require "thrift"
# require "gen-rb/ontology_service"

class OntologyClient

  attr_reader :client, :transport
  def initialize
    @transport = Thrift::Socket.new('localhost', 9090)
    protocol = Thrift::BinaryProtocol.new(transport)
    @client = OntologyService::Client.new(protocol)
    @transport.open()
  end

end
