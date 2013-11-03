require 'nokogiri'
require 'open-uri'
require "active_support/core_ext"
require 'open-uri'
require 'fileutils'

restaurants = []

def get_menu(hash)
  meals = []

  restaurant_page = Nokogiri::HTML(open("http://hrum.com.ua/menu/adriano")).css('.menu_list').each do |rest|
      meal_category = rest.css('.menu_btn').text.strip
      category_meal = rest.css('.menu_togle li').each do |meal|
        next unless meal.at_css('.pic')

        key       = meal['id']
        meal_name = meal.css('.about_box .title a').text.strip
        meal_text = meal.css('.about_box .text').text.strip
        meal_price= meal.css('.price_box div.price').text

        meal_hash = meal.css('.pic a')[0]['href'].sub('/menu/', '').split('/')
        parent    = meal_hash[0]
        hash      = meal_hash[1]

        image_path_orig  = meal.css('.pic .big_pic img')[0]['data-original']
        image_path_local = "../public/media/#{parent}/food/" + image_path_orig.sub('/images/', '')  + '.jpg' 
        image = image_path_local.sub('../public', '')

=begin
        dirname = File.dirname(image_path_local)
        unless File.directory?(dirname)
          FileUtils.mkdir_p(dirname)
        end
        open(image_path_local, 'wb') do |file|
          file << open("http://hrum.com.ua#{image_path_orig}").read
        end
=end

        m = {
          key: key,
          hash: hash,
          parent: parent,
          category: meal_category,
          image: image,
          price: meal_price.sub('грн.', '').strip,
          currency: 'UAH',
          name: meal_name,
          description: meal_text
        }

        meals.push m
      end
    end   

  meals
end

doc = Nokogiri::HTML(open('http://hrum.com.ua/restaurants/all/kiev')).css('.item_box').each do |item|

  title = item.css('.title').text.strip
  key = item.css('.hd_inner a.restaurant_title')[0]['href'].sub('/menu/', '')
  logo_src = item.css('.item_logo img')[0]['src']
  logo_alt = item.css('.item_logo img')[0]['alt']

  geo_lng = item.css('.address .gmaper')[0]['data-lng'].to_f
  geo_lat = item.css('.address .gmaper')[0]['data-lat'].to_f
  geo_title=item.css('.address .gmaper')[0]['data-title']

  note = item.css('.note').text.strip

  min_cost      = item.css('.info_box li')[1].css('span').text.sub('грн.', '').strip
  delivery_time = item.css('.info_box li')[5].css('span').text.sub(/[^\d]+/, '')
  delivery      = item.css('.info_box li')[3].css('span').text.split
  category      = item.css('.info_box li')[2].css('span').text.split(/[,.\\\/]/).map(&:strip)

  puts "exporting #{title}" 

  menu = get_menu key

=begin
  image_path = "../public/media/logo/" + logo_src.sub('/images/', '')  + '.jpg' 
  open(image_path, 'wb') do |file|
    file << open("http://hrum.com.ua#{logo_src}").read
  end
=end

  r = {
      name: key,
      title: title,
      lat: geo_lat,
      lng: geo_lng,
      logo: {
        src: logo_src.sub('/images/', '/media/logo/') + '.jpg',
        alt: logo_alt
      },
      address: {
        street: geo_title
      },
      info: {
        note: note,
      },
      category: category,
      delivery: {
        min_cost: min_cost.sub('грн.', '').strip,
        time: delivery_time,
        cost: delivery[0],
        currency: 'UAH'
      },
      menu: menu
  }

  restaurants.push r
end


rests = restaurants.map(&:to_json).join(",\n")

rests = "[#{rests}]"

File.open('restaurants.json', 'w') { |file| file.write rests }