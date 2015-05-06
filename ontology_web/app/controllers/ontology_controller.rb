class OntologyController < ApplicationController

  skip_before_action :verify_authenticity_token

  def index
    @left_bracket = "{{"
    @right_bracket = "}}"
  end

  def  origin
    o_client = OntologyClient.new
    res  = o_client.client.getInitValues()
    o_client.transport.close()
    render json: res
  end

  def reason
    o_client = OntologyClient.new
    sensor = SenseorValues.new
    sensor.exH2s = params[:exH2s].to_f
    sensor.exOxy2 = params[:exOxy2].to_f
    sensor.exPH2 = params[:exPH2].to_f
    sensor.exTempl = params[:exTempl].to_f
    sensor.exampleConducitivity2 = params[:exampleConducitivity2].to_f
    res = o_client.client.perfomReason(sensor)
    o_client.transport.close()
    render json: res
  end



end
