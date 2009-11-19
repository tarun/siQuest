class MainController < ApplicationController
  layout 'main'
  require 'json'
  
  def index
    @analyzers = ['Simple', 'Snowball', 'Standard', 'Stop', 'Whitespace']
    redirect_to :action => 'search'
  end

  def search
    create_variables

    if params[:files]
      @analyzer = params[:options][:analyzer]

      params[:files].each do |key, file|
        put_file file unless file.blank?
      end

      files = ""
      @list_of_files.each { |file|
        files << "#{@public_dir}uploaded_files/#{file};"
      }

      results = 10
      start = 0

      base_url = "http://localhost:8080/siQuestService/SiQuestREST?"
      url = "#{base_url}&files=#{URI.encode(files)}&results=#{results}&start=#{start}&analyzer=#{@analyzer}"
      resp = Net::HTTP.get_response(URI.parse(url))
      data = resp.body

      @jsonResponse = JSON.parse(data)
    end
  end

  def create_variables
    @analyzers = ['Simple', 'Snowball', 'Standard', 'Stop', 'Whitespace']
    @list_of_files = Array.new
    @public_dir = 'C:/Users/Tarun/Documents/Code/tmp/'
    @tmp_upload_dir = 'tmp/uploaded_files'

    logger.debug @public_dir+" - Public Dir"
    logger.debug @tmp_upload_dir+" tmp Upload Dir"

    @tmp_dir = FileUtils.pwd+"/#{@tmp_upload_dir}/"
    logger.debug @tmp_dir+""
  end  

  def create_tmp_directory
    FileUtils.mkdir_p @tmp_dir
  end

  def put_file (file)
    name = File.basename file.original_filename
    name = name.gsub ' ', '_'

    upload_file file, name
    @list_of_files << name
    FileUtils.cp_r @tmp_dir, @public_dir
  end

  def upload_file (file, name)
    File.open @tmp_dir+name, 'wb' do |place|
      place.write file.read
    end
  end

  def delete_uploaded_files
    begin
      FileUtils.remove_entry_secure @tmp_dir
    rescue Errno::EACCES
      logger.debug "---error deleting files at #{@tmp_dir}"
    end
  end

end
