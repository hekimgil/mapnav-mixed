json.array!(@albums) do |album|
  json.extract! album, :id, :title, :center, :owner
  json.url album_url(album, format: :json)
end
